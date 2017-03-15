package com.alfred.androidstudy.dragger2;

import javax.inject.Inject;

/**
 * Engine类是依赖提供方,因此需要在它的构造函数上添加@Inject
 * Created by Alfred on 2017/3/13.
 */

public class Engine {
    @Inject
    Engine(){}

    public void run(){
        System.out.println("runnung ======");
    }


}
