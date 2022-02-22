package com.wang.vire.utils;

import com.wang.vire.exception.ParamException;
import org.springframework.stereotype.Component;

/**
 * 在web层检查错误用
 */
@Component
public class WebCheck {

    public static void isError(Object i) throws ParamException {
        if (i instanceof Integer && (Integer)i <= 0) {
            switch ((Integer)i) {
                case 0:
                    throw new ParamException("必要参数为空",405);
                case -1:
                    throw new ParamException("已存在",406);
                case -2:
                    throw new ParamException("参照表中不存在对应值",407);
                case -3:
                    throw new ParamException("操作对象不存在",408);
                case -4:
                    throw new ParamException("对应操作失败", 409);
                case -5:
                    throw new ParamException("重复操作", 410);
                case -6:
                    throw new ParamException("权限不足", 411);
            }
        }
    }
}
