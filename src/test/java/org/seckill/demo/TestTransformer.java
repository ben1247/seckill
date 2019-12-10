package org.seckill.demo;


import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;

public class TestTransformer {

    public static void main(String [] args){


        String cporderid = "20191110225732479774144";
        while(cporderid.indexOf("0") == 0){
            cporderid = cporderid.substring(1, cporderid.length());
        }

        System.out.println(cporderid);

    }

}
