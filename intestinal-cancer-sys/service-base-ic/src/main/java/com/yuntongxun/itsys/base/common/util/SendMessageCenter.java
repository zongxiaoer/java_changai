package com.yuntongxun.itsys.base.common.util;


import com.yuntongxun.itsys.base.common.constans.Constans;
import org.apache.commons.lang.StringUtils;

public class SendMessageCenter {


    /**
     *
     * @param sendUser   发送人
     * @param acceptUser  接受人
     * @param meaasgeType  消息类型
     * @param text        消息文本
     * @param sid
     * @param meaasgeTextType   消息文本类型
     * @param courierNumber    快递编号
     * @return
     */

    /**
     * 1  异常    违反方案
     *           sid
     *           已经退出研究
     *           sid、text
     *           诊断为结直肠癌
     *           sid
     *申请|发放编辑
     *          快递模块
     *          sendUser、acceptUser、courierNumber
     *          管理模块
     *          sendUser、acceptUser、text（模块+sid）
     *通知发放
     *
     *
     *
     *
     *
     */
    public static String getMessage(String sendUser, String acceptUser, String meaasgeType, String text, String sid, String meaasgeTextType, String courierNumber,String remark) {


/*

    //3 申请编辑

       if（快递模块）

    {
        sendUser 已向您 acceptUser 提交了 编辑 “text（快递信息 + 运单号）”的申请，请及时处理
    }else if(管理模块)

    {

    }

    sendUser 已向您
    acceptUser 提交了
    编辑 “text（xx管理+sid）”的申请，
    请及时处理


            解锁


        if（快递模块）

    {
        sendUser 已通过了您 acceptUser 提交了 编辑 “text（快递信息 + 运单号）”的申请，请及时处理
    }else

    {

    }

    sendUser已通过了您acceptUser 提交的编辑“text（xx管理+sid）”的申请，请及时处理


    //发放
    sendUser 已向您acceptUser下发了
    sid 的粪便DNA结果，请及时查看
*/


        StringBuffer stringBuffer = new StringBuffer();
        //异常类型
        if (Constans.meaasge_typpe2.equals(meaasgeType)) {
            if (Constans.meaasge_text_typpe1.equals(meaasgeTextType)) {//违反方案
                stringBuffer.append(sid + Constans.meaasge_text_typpe1);
            } else if (Constans.meaasge_text_typpe2.equals(meaasgeTextType)) {//已经退出研究
                stringBuffer.append(sid + Constans.meaasge_text_typpe2 + "，原因" + text);
            } else if (Constans.meaasge_text_typpe3.equals(meaasgeTextType)) {//诊断为结直肠癌
                stringBuffer.append(sid + Constans.meaasge_text_typpe3);
            } else if(Constans.meaasge_text_typpe7.equals(meaasgeTextType)){//重新入组
                stringBuffer.append(sid + Constans.meaasge_text_typpe7);
            }
            stringBuffer.append("，请及时关注。");
        }

        //申请编辑
        if (Constans.meaasge_typpe3.equals(meaasgeType)) {
            remark=StringUtils.isEmpty(remark)?"":"，编辑原因："+remark;
            if (Constans.meaasge_text_typpe4.equals(meaasgeTextType)) {//快递模块
                stringBuffer.append(sendUser + "已向您" + acceptUser + "提交了编辑(快递信息-运单号" + courierNumber + ")的申请"+remark);
            } else {//管理模块
                stringBuffer.append(sendUser + "已向您" + acceptUser + "提交了编辑(" + text + ")的申请"+remark);
            }
            stringBuffer.append("，请及时处理。");
        }

        //编辑解锁
        if (Constans.meaasge_typpe4.equals(meaasgeType)) {
            if (Constans.meaasge_text_typpe4.equals(meaasgeTextType)) {//快递模块
                stringBuffer.append(sendUser + "已通过了您" + acceptUser + "提交编辑(快递信息-运单号" + courierNumber + ")的申请");
            } else {//管理模块
                stringBuffer.append(sendUser + "已通过了您" + acceptUser + "提交编辑(" + text + ")的申请");
            }
            stringBuffer.append("，请及时处理。");
        }

        //通知发放
        if (Constans.meaasge_typpe5.equals(meaasgeType)) {
            stringBuffer.append(sendUser + "已向您" + acceptUser + "下发了" + sid + "的粪便DNA结果，请及时查看。");
        }

        if(Constans.meaasge_typpe1.equals(meaasgeType)){

            stringBuffer.append(sid+text+"已被停诊，请及时与受试者联系!");
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
       // String message = getMessage("zongtong", "ziji", Constans.meaasge_typpe2, "", "123", Constans.meaasge_text_typpe1, "");
      //  System.out.println(message);
    }
}