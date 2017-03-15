package com.alfred.androidstudy.dragger2;

import dagger.Component;

/**
 * 创建一个用@Component标注的接口CarComponent,这个CarComponent其实就是一个注入器,用来将Engine注入到Car中
 * Created by Alfred on 2017/3/13.
 */

@Component
public interface CarComponent {
    void inject(Car car);
}
