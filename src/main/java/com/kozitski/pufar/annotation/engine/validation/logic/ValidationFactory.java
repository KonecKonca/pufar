package com.kozitski.pufar.annotation.engine.validation.logic;

import com.kozitski.pufar.annotation.engine.validation.annotation.Validate;
import com.kozitski.pufar.annotation.engine.validation.annotation.ValidateParameterTransfer;
import com.kozitski.pufar.annotation.engine.validation.entity.Student;
import com.kozitski.pufar.annotation.engine.validation.entity.Validatable;

import java.lang.reflect.*;

@Deprecated
public class ValidationFactory{

    // попробую не делать никаких прокси, а просто пройтись по объекту и проверять аннотированные поля
    public static Validatable createValidObject(Validatable targetObject) {

        return  (Validatable)Proxy.newProxyInstance(
                targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                new ValidationInvocationHandler(targetObject));

    }

    private static class ValidationInvocationHandler implements InvocationHandler {

        private Object targetObject;
        private ValidationInvocationHandler(Object targetObject) {
            this.targetObject = targetObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            ValidationLogic logic = new ValidationLogic();

            Method realMethod = targetObject.getClass().getMethod(method.getName(), (Class[]) method.getGenericParameterTypes());
            Validate annotation = realMethod.getAnnotation(Validate.class);

            if(annotation != null){
                ValidateParameterTransfer transfer = new ValidateParameterTransfer();

                transfer.setMinLength(annotation.minLength());
                transfer.setMaxLength(annotation.maxLength());
                transfer.setRegExp(annotation.validateRegExp());
                transfer.setForbiddenValue(annotation.forbiddenValue());

                logic.onInvoke(transfer, realMethod, args);

                try {
                    return method.invoke(targetObject, args);
                }
                catch (InvocationTargetException e){
                    throw e.getCause();
                }
            }
            else {
                // в случае если допустимо отсутствие аннотации у метода
                return null;
            }

        }


    }

}
