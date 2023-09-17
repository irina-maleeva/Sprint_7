package org.example.order;
import org.example.model.Order;

public class OrderGenerator {

    public static Order getOrderGreyColor(){
        return new Order(
                "Ян",
                "Ли",
                "3-я улица Ямского поля, 6",
                "Черкизовская",
                "+79160000002",
                "3",
                "2022-12-24",
                "Позвоните мне",
                new String[]{"GREY"});
    }
    public static Order getOrderBlackColor(){
        return new Order("Яна", "Рождественская", "Зубовский бульвар, 5", "Преображенская площадь", "+791600000027",
                "2", "2022-12-24", "Лучше стучать", new String[]{"BLACK"});
    }
    public static Order getOrderTwoColors(){
        return new Order("Роман", "Александров", "Новомытищенский проспект, 33, корпус 1", "Перово", "+12345678901",
                "2", "2022-12-24", "", new String[]{"GREY", "BLACK"});
    }

    public static Order getOrderNoColors(){
        return new Order("Аркадий", "Петров", "Тверская, 5", "Сокол", "+791600006627",
                "2", "2022-12-24", "", null);
    }

}
