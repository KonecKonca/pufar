package com.kozitski.pufar.annotation.engine.di.engine.annotation;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;

public class InjectionServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        // amy configs for

        try {

            ApplicationContext context = (ApplicationContext) Class.forName("com.kozitski.pufar.annotation.engine.di.engine.annotation.RealApplicationContext").newInstance();
            context.init("xmlPath");

            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                Inject annotation = field.getAnnotation(Inject.class);
                if(annotation != null){
                    String beanName = annotation.beanName();
                    Object bean = context.getBean(beanName);
                    if(bean == null){
                        throw new RuntimeException("Can not fount nessary class for bean initialization");
                    }
                    field.set(this, bean);
                }


            }

        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}
