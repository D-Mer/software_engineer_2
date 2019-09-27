package bussiness;

import com.alibaba.fastjson.JSON;
import com.example.cinema.CinemaApplicationTest;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallVO;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public class businessTest extends CinemaApplicationTest {

    @Test
    public void hallTest() throws Exception {

        //测试获取所有影厅信息
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/hall/all")
                .accept(MediaType.APPLICATION_JSON))
                // .andExpect(MockMvcResultMatchers.status().isOk())             //等同于Assert.assertEquals(200,status);
                // .andExpect(MockMvcResultMatchers.content().string("hello lvgang"))    //等同于 Assert.assertEquals("hello lvgang",content);
                .andReturn();
        int status=mvcResult.getResponse().getStatus();                 //得到返回代码
        String content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
        Assert.assertEquals("{\"success\":true,\"message\":null,\"content\":[{\"id\":1,\"name\":\"刘涛的后宫\",\"row\":5,\"column\":10},{\"id\":2,\"name\":\"许竣博的后花园\",\"row\":8,\"column\":12},{\"id\":3,\"name\":\"周际宇的狗窝\",\"row\":10,\"column\":10}]}",content);            //断言，判断返回的值是否正确

        //测试添加影厅是否成功
        mvcResult= mockMvc.perform(MockMvcRequestBuilders.post("/hall/addHall")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"刘涛的后宫2\",\"row\":6,\"column\":11}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确

        //测试更新影厅是否成功
        mvcResult= mockMvc.perform(MockMvcRequestBuilders.post("/hall/updateHall")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":3,\"name\":\"刘涛的后宫3\",\"row\":7,\"column\":12}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确

        mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/hall/all")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
    }

    @Test
    public void sendCouponTest() throws Exception{
        //测试获取所有符合条件的会员信息
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/coupon/getVIPList")
                .param("target_amount","100")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status=mvcResult.getResponse().getStatus();                 //得到返回代码
        String content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
        Assert.assertEquals("{\"success\":true,\"message\":\"获取成功\",\"content\":[{\"id\":16,\"username\":\"814775538\",\"cost\":460.0},{\"id\":3,\"username\":\"test\",\"cost\":240.0}]}",content);            //断言，判断返回的值是否正确

        //获取所有优惠券
        mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/coupon/getAllCoupon")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
        Assert.assertEquals("{\"success\":true,\"message\":\"获取成功\",\"content\":[{\"id\":1,\"description\":\"测试优惠券\",\"name\":\"春季电影节\",\"targetAmount\":20.0,\"discountAmount\":5.0,\"startTime\":\"2019-04-21T01:47:54.000+0800\",\"endTime\":\"2019-04-24T01:47:59.000+0800\"},{\"id\":5,\"description\":\"测试优惠券\",\"name\":\"品质联盟\",\"targetAmount\":30.0,\"discountAmount\":4.0,\"startTime\":\"2019-04-21T05:14:46.000+0800\",\"endTime\":\"2019-04-25T05:14:51.000+0800\"},{\"id\":6,\"description\":\"春节电影节优惠券\",\"name\":\"电影节优惠券\",\"targetAmount\":50.0,\"discountAmount\":10.0,\"startTime\":\"2019-04-21T05:15:11.000+0800\",\"endTime\":\"2019-04-22T05:14:56.000+0800\"},{\"id\":8,\"description\":\"测试优惠券\",\"name\":\"测试用优惠券\",\"targetAmount\":100.0,\"discountAmount\":50.0,\"startTime\":\"2019-06-12T19:48:18.000+0800\",\"endTime\":\"2019-04-27T00:00:00.000+0800\"},{\"id\":11,\"description\":\"辉哥的优惠券\",\"name\":\"辉哥的优惠券\",\"targetAmount\":20.0,\"discountAmount\":10.0,\"startTime\":\"2019-06-12T19:46:47.000+0800\",\"endTime\":\"2019-06-29T00:00:00.000+0800\"},{\"id\":12,\"description\":\"刘涛的优惠券\",\"name\":\"刘涛的优惠券\",\"targetAmount\":50.0,\"discountAmount\":30.0,\"startTime\":\"2019-06-06T00:00:00.000+0800\",\"endTime\":\"2019-06-30T00:00:00.000+0800\"}]}",content);            //断言，判断返回的值是否正确

        //赠送优惠券
        mvcResult= mockMvc.perform(MockMvcRequestBuilders.post("/coupon/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[" +
                        "{" +
                        "\"couponId\":5," +
                        "\"userId\":16" +
                        "}," +
                        "{" +
                        "\"couponId\":8," +
                        "\"userId\":16" +
                        "}" +
                        "]")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果
        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
    }

    @Test
    public void manageAccountTest() throws Exception{
        //测试获取所有用户信息
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/admin/getAllUser")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status=mvcResult.getResponse().getStatus();                 //得到返回代码
        String content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
        Assert.assertEquals("{\"success\":true,\"message\":\"获取所有影院角色信息成功\",\"content\":[{\"id\":1,\"username\":\"root\",\"password\":\"123456\",\"identity\":\"管理员\"},{\"id\":2,\"username\":\"employee\",\"password\":\"123456\",\"identity\":\"员工\"},{\"id\":3,\"username\":\"test\",\"password\":\"123456\",\"identity\":\"观众\"},{\"id\":5,\"username\":\"test1\",\"password\":\"123456\",\"identity\":\"观众\"},{\"id\":7,\"username\":\"test121\",\"password\":\"123456\",\"identity\":\"观众\"},{\"id\":8,\"username\":\"root2\",\"password\":\"123456\",\"identity\":\"员工\"},{\"id\":10,\"username\":\"roottt\",\"password\":\"123123\",\"identity\":\"观众\"},{\"id\":12,\"username\":\"zhourui\",\"password\":\"123456\",\"identity\":\"观众\"},{\"id\":13,\"username\":\"abc123\",\"password\":\"abc123\",\"identity\":\"观众\"},{\"id\":15,\"username\":\"dd\",\"password\":\"123\",\"identity\":\"观众\"},{\"id\":16,\"username\":\"814775538\",\"password\":\"814775538\",\"identity\":\"观众\"}]}",content);            //断言，判断返回的值是否正确

        //增加用户
        mvcResult= mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"username\":\"ttttt\",\n" +
                        "\t\"password\":\"ttttt\",\n" +
                        "\t\"identity\":\"管理自创\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确

        //更新用户
        mvcResult= mockMvc.perform(MockMvcRequestBuilders.post("/admin/updateUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"id\":2,\n" +
                        "\t\"username\":\"333222\",\n" +
                        "\t\"password\":\"123456789\",\n" +
                        "\t\"identity\":\"管理修改\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
        Assert.assertEquals("{\"success\":true,\"message\":\"修改影院角色信息成功\",\"content\":{\"id\":2,\"username\":\"333222\",\"password\":\"123456789\",\"identity\":\"管理修改\"}}",content);

        //删除用户
        mvcResult= mockMvc.perform(MockMvcRequestBuilders.post("/admin/delUser")
                .param("id","2")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        status=mvcResult.getResponse().getStatus();                 //得到返回代码
        content=mvcResult.getResponse().getContentAsString();    //得到返回结果

        Assert.assertEquals(200,status);                        //断言，判断返回代码是否正确
    }


}
