package com.alfred.androidstudy.dragger2;

import javax.inject.Inject;

/**
 * Car类是需求依赖方,依赖了Engine类,因此需要在类变量Engine上添加@Inject来告诉Dragger2来为自己提供依赖
 * Created by Alfred on 2017/3/13.
 */

public class Car {
    @Inject
    Engine mEngine;

    public Car(){
        DaggerCarComponent.builder().build().inject(this);
    }

    public Engine getEngine(){
        return this.mEngine;
    }

}
