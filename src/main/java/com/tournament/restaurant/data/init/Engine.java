package com.tournament.restaurant.data.init;

import com.tournament.restaurant.data.entities.*;
import com.tournament.restaurant.data.service.CartService;
import com.tournament.restaurant.data.service.FoodService;
import com.tournament.restaurant.data.service.SessionService;
import com.tournament.restaurant.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Engine implements CommandLineRunner {

    @Autowired
    private final UserService userService;

    @Autowired
    private final SessionService sessionService;

    @Autowired
    private final FoodService foodService;
    @Autowired
    private final CartService cartService;


    public Engine(UserService userService, SessionService sessionService, FoodService foodService, CartService cartService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.foodService = foodService;
        this.cartService = cartService;
    }


    @Override
    public void run(String... args) throws Exception {
        Session session = new Session();
        session.setAuthToken("testToken2");
        session.setCreatedTime(new Timestamp(System.currentTimeMillis()));

        // USER place ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        User user = new User();
        user.setAddress("testAddress");
        user.setFullName("ali zinal22222222222");
        user.setUsername("taurus366");
        user.setPassword("1234");
        user.setPhoneNumber("123456789");
        user.setRole(Role.ADMIN);
        session.setUser(user);
        user.setSession(session);

        // USER place ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        userService.postUser(user);

        Food food = new Food();
        food.setName("Дюнер");
        food.setDescription("Пилешко месо, печено на вертикален грил, увито в лаваш, с добавка подправки, сосове и салата от пресни зеленчуци");
        food.setImageUrl("https://donerandgyros.co.uk/uploads/gallery_boxes_items/image/500x470/Beef_doner.jpg");
        food.setPrice(4.50);

        Food food2 = new Food();
        food2.setName("Адана кебаб");
        food2.setDescription("Включваща само мъжко агнешко месо, червени чушки и мазнина от опашка, смлени заедно. Сервира се с овъглени чушки и домати, салата от лук-смрадлика и магданоз и лаваш");
        food2.setImageUrl("https://img.pixers.pics/pho_wat(s3:700/FO/42/16/08/84/700_FO42160884_55fdbc4f27df41011f704ea99f45608b.jpg,700,467,cms:2018/10/5bd1b6b8d04b8_220x50-watermark.png,over,480,417,jpg)/stickers-adana-kebap.jpg");
        food2.setPrice(3.99);

        Food food3 = new Food();
        food3.setName("Адана кебаб Свинско");
        food3.setDescription("Включваща само мъжко агнешко месо, червени чушки и мазнина от опашка, смлени заедно. Сервира се с овъглени чушки и домати, салата от лук-смрадлика и магданоз и лаваш");
        food3.setImageUrl("https://img.pixers.pics/pho_wat(s3:700/FO/42/16/08/84/700_FO42160884_55fdbc4f27df41011f704ea99f45608b.jpg,700,467,cms:2018/10/5bd1b6b8d04b8_220x50-watermark.png,over,480,417,jpg)/stickers-adana-kebap.jpg");
        food3.setPrice(8.99);

        Food food4 = new Food();
        food4.setName("Меню 1");
        food4.setDescription("Включваща само мъжко агнешко месо, червени чушки и мазнина от опашка, смлени заедно. Сервира се с овъглени чушки и домати, салата от лук-смрадлика и магданоз и лаваш");
        food4.setImageUrl("https://order.bg/resources/images/cuisine/asian.jpg");
        food4.setPrice(14.00);

        Food food5 = new Food();
        food5.setName("Пица");
        food5.setDescription("Включваща кашкавал,гъби,бекон,пресни домати,маслини,грах,яйца");
        food5.setImageUrl("http://pzdnes.com/wp-content/uploads/2019/11/4-%D0%B7%D0%B4%D1%80%D0%B0%D0%B2%D0%BE%D1%81%D0%BB%D0%BE%D0%B2%D0%BD%D0%B8-%D1%80%D0%B5%D1%86%D0%B5%D0%BF%D1%82%D0%B8-%D0%B7%D0%B0-%D0%BF%D0%B8%D1%86%D0%B0_page-0001-e1574325509853-864x400_c.jpg");
        food5.setPrice(7.99);

        foodService.postFood(food);
        foodService.postFood(food2);
        foodService.postFood(food3);
        foodService.postFood(food4);
        foodService.postFood(food5);



    }
}
