package com.kozitski.pufar.validation.aspect.info;

import java.lang.annotation.Annotation;

//public aspect StringValidationAspect {

//    pointcut stringValidation() :
//            execution(public * ((@MessageValid *)+).*(..)) && within(@MessageValid *);
////            execution(* (@MessageValid *).*(..));
//
//    before() : stringValidation(){
//        Object[] args = thisJoinPoint.getArgs();
//
//        System.out.println("Validate: ");
//        for (Object o : args){
//
//            Annotation[] annotations = o.getClass().getAnnotations();
//            for(Annotation annotation : annotations){
//                System.out.println(annotation);
//            }
//
////            System.out.println("------" + ((String)o));
//        }
//
//
//        System.out.println("!!!!!!!!!!!!!");
//
//    }


//}



//    @Before("execution(@Entity * com.kozitski.pufar..*WithValid*(..))")