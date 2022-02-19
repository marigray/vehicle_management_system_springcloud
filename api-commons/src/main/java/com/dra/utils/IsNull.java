package com.dra.utils;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class IsNull {
    private Object[] objects;


    public boolean check(){
        for (Object object : objects) {
            if (object==null){
                return true;
            }else if (object instanceof String){
                if (object.equals("")){
                    return true;
                }
            }

        }
        return false;
    }

    public static void main(String[] args) {
        String[] s = {null,"1"};
        System.out.println(new IsNull(s).check());
    }
}
