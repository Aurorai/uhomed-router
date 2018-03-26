package com.uhomed.router.web.controller.admin;

import org.springframework.web.servlet.ModelAndView;

import com.uhomed.router.Result;

/**
 * @author
 * @version $$Id: , v 0.1    Exp $$
 */
public class BaseController {

    protected void setResultModel(ModelAndView result , Result r){
        if(r.isSuccess()){
            this.setSuccessful(result,r.getMessage());
        }else {
            this.setFailMessage(result,r.getMessage());
        }
    }

    /**
     * set信息
     *
     * @param message
     */
    protected void setFailMessage(ModelAndView result, String message, String... code) {
        result.addObject("success",false);
        result.addObject("message",message);
        if (code.length != 0) {
            result.addObject( "code", code[ 0 ] );
        }
    }

    protected void setSuccessful(ModelAndView result, String message) {
        result.addObject("success",true);
        result.addObject("message",message);
        result.addObject( "code", "000000" );
    }
}
