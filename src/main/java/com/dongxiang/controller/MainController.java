package com.dongxiang.controller;

import com.dongxiang.model.entity.UserEntity;
import com.dongxiang.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dzkan on 2016/3/8.
 */
@Controller
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    // 自动装配数据库接口，不需要再写原始的Connection来操作数据库
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "apiIndex";
    }


//    @Autowired
//    private UserInfoComponent userInfoComponent;
//    @RequestMapping(value = "/users", method = RequestMethod.GET)
//    public String getUsers(ModelMap modelMap) {
//        // 查询user表中所有记录
////        List<UserEntity> userList = userRepository.findAll();
////        loginApp.login();
//        logger.warn("getUsers");
//        // 将所有记录传递给要返回的jsp页面，放在userList当中
//        modelMap.addAttribute("userList", userInfoComponent.getAllUsers(1));
//
//        // 返回pages目录下的admin/users.jsp页面
//        return "admin/users";
//    }

//    @Autowired
//    LoginApp loginApp;
//    /**
//     * 测试返回json格式内容。
//     * @return
//     */
//    @RequestMapping(value = "/admin/usersJson", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//    @ResponseBody /*只需要响应字符串内容，可以自由控制返回的数据内容*/
//    public String testJson(){
//        Gson gson = new Gson();
//        logger.warn("getUsers");
//        loginApp.login();
//        // 将所有记录传递给要返回的jsp页面，放在userList当中
//        List<UserEntity> list = userInfoComponent.getAllUsers(1);
//        ArrayList<UserRespBody.User> result = new ArrayList<UserRespBody.User>();
//        for(UserEntity ue:list){
//            UserRespBody.User user = new UserRespBody.User();
//            user.id = ue.getId();
//            user.name=ue.getNickname();
//            result.add(user);
//        }
//        return gson.toJson(new ApiData("UserInfo",new UserRespBody(result)));
//    }
//
//

    // get请求，访问添加用户 页面
    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String addUser() {
        // 返回 admin/addUser.jsp页面
        return "admin/addUser";
    }

    // post请求，处理添加用户请求，并重定向到用户管理页面
    @RequestMapping(value = "/users/addP", method = RequestMethod.POST)
    public String addUserPost(@ModelAttribute("user") UserEntity userEntity) {
        // 注意此处，post请求传递过来的是一个UserEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'user'，并创建这个对象

        // 数据库中添加一个用户，该步暂时不会刷新缓存
        //userRepository.save(userEntity);
        System.out.println(userEntity.getFirstName());
        System.out.println(userEntity.getLastName());

        // 数据库中添加一个用户，并立即刷新缓存
        userRepository.saveAndFlush(userEntity);

        // 重定向到用户管理页面，方法为 redirect:url
        return "redirect:/users";
    }

    // 查看用户详情
    // @PathVariable可以收集url中的变量，需匹配的变量用{}括起来
    // 例如：访问 localhost:8080/admin/users/show/1 ，将匹配 id = 1
    @RequestMapping(value = "/users/show/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") Integer userId, ModelMap modelMap) {

        // 找到userId所表示的用户
        UserEntity userEntity = userRepository.findOne(userId);

        // 传递给请求页面
        modelMap.addAttribute("user", userEntity);
        return "admin/userDetail";
    }

    // 更新用户信息 页面
    @RequestMapping(value = "/users/update/{id}", method = RequestMethod.GET)
    public String updateUser(@PathVariable("id") Integer userId, ModelMap modelMap) {

        // 找到userId所表示的用户
        UserEntity userEntity = userRepository.findOne(userId);

        // 传递给请求页面
        modelMap.addAttribute("user", userEntity);
        return "admin/updateUser";
    }

    // 更新用户信息 操作
    @RequestMapping(value = "/users/updateP", method = RequestMethod.POST)
    public String updateUserPost(@ModelAttribute("userP") UserEntity user) {

        // 更新用户信息
        userRepository.updateUser(user.getNickname(), user.getFirstName(),
                user.getLastName(), user.getPassword(), user.getId());
        userRepository.flush(); // 刷新缓冲区
        return "redirect:/users";
    }

    // 删除用户
    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Integer userId) {

        // 删除id为userId的用户
        userRepository.delete(userId);
        // 立即刷新
        userRepository.flush();
        return "redirect:/users";
    }
}
