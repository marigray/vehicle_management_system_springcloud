package com.ycx.lend.utils;

import com.ycx.lend.exception.ParamException;
import org.springframework.stereotype.Component;

/**
 * 在web层检查错误用
 */
@Component
public class WebCheck {

    public static void isError(Object i) throws ParamException {
        if (i instanceof String) {
            if (ServiceUtils.isNumeric((String) i)) {
                i = Integer.parseInt((String) i);
            }
        }
        if (i instanceof Integer) {
            if ((Integer) i <= -10) {
                switch ((Integer) i+10) {
                    case 0:
                        throw new ParamException("嵌套异常，必要参数为空", 401);
                    case -1:
                        throw new ParamException("嵌套异常，主键重复", 402);
                    case -2:
                        throw new ParamException("嵌套异常，参照表中不存在对应值", 403);
                    case -3:
                        throw new ParamException("嵌套异常，操作对象不存在", 404);
                    case -4:
                        throw new ParamException("嵌套异常，对应操作失败", 405);
                    case -5:
                        throw new ParamException("嵌套异常，重复操作", 406);
                    case -6:
                        throw new ParamException("嵌套异常，权限不足", 407);
                }

            }
            if ((Integer) i <= 0) {
                switch ((Integer) i) {
                    case 0:
                        throw new ParamException("必要参数为空", 401);
                    case -1:
                        throw new ParamException("主键重复", 402);
                    case -2:
                        throw new ParamException("参照表中不存在对应值", 403);
                    case -3:
                        throw new ParamException("操作对象不存在", 404);
                    case -4:
                        throw new ParamException("对应操作失败", 405);
                    case -5:
                        throw new ParamException("重复操作", 406);
                    case -6:
                        throw new ParamException("权限不足", 407);
                    case -7:
                        throw new ParamException("非法操作", 408);
                }
            }
        }

    }
}
