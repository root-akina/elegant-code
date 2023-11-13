<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left" >
      <div class="title-container">
        <h3 class="title">系统登录</h3>
        <div class="login_header">
          <a @click="loginForm.loginType='user-username'" :class="{active:loginForm.loginType=='user-username'}">密码登录</a>
          <a @click="loginForm.loginType='user-mobile'" :class="{active:loginForm.loginType=='user-mobile'}">短信登录</a>
        </div>
      </div>

      <div class="login_content">
        <!-- 在cur==0时此板块显示 其他时候此板块不显示 -->
        <div v-if="loginForm.loginType=='user-username'" class="Cbody_item">
          <el-form-item prop="username">
                <span class="svg-container">
                  <svg-icon icon-class="user" />
                </span>
            <el-input  ref="mobile" v-model="loginForm.username" :placeholder="$t('login.username')"
                       name="username" type="text" tabindex="1" auto-complete="on"/>
          </el-form-item>

          <el-form-item prop="password">
                <span class="svg-container">
                  <svg-icon icon-class="password" />
                </span>
            <el-input :key="passwordType" ref="password" v-model="loginForm.password" :type="passwordType"
                      :placeholder="$t('login.password')" name="password" tabindex="2" auto-complete="on"
                      @keyup.enter.native="handleLogin" />
            <span class="show-pwd" @click="showPwd">
                  <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
                </span>
          </el-form-item>
        </div>
        <!-- 在cur==1时此板块显示 其他时候此板块不显示 -->
        <div v-if="loginForm.loginType=='user-mobile'" class="Cbody_item">
          <el-form-item prop="mobile">
                <span class="svg-container">
                  <svg-icon icon-class="user" />
                </span>
            <el-input ref="mobile" v-model="loginForm.mobile" placeholder="请输入手机号" name="mobile" type="text" tabindex="1" auto-complete="on"/>
          </el-form-item>
          <el-form-item prop="authCode">
                <span class="svg-container">
                  <svg-icon icon-class="password" />
                </span>
            <el-input ref="authCode" v-model="loginForm.authCode" type="text" placeholder="请输入验证码" name="authCode" abindex="2" maxlength="6" auto-complete="on" @keyup.enter.native="handleLogin" />
            <span class="show-pwd">
                  <el-button :loading="sending" :disabled="sendDisabled" size="small" @click="onSendSms">{{ sendButtonText}}</el-button>
            </span>
          </el-form-item>

        </div>
      </div>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">登录</el-button>
      <div style="position:relative">
        <div class="social-signup-container">
          <div class="sign-btn" @click="doShow()">
            <span class="wx-svg-container"><svg-icon icon-class="wechat" class="icon" /></span>
          </div>
        </div>
      </div>
    </el-form>
    <el-dialog :title="$t('login.thirdparty')" :visible.sync="showDialog">
      <div id="loginCode" class="loginCode" ></div>
    </el-dialog>

  </div>
</template>

<script>
  import global from '@/api/global'
  import {smsSend,findentErpriseVo} from "@/api/authority/user";
  import {validUsername,validMobile} from '@/utils/validate'
  import Cookie from "js-cookie";
  import '@/api/wxLogin';

  export default {
    name: "Login",
    data() {
      // 参数校验
      const validateUsername = (rule, value, callback) => {
        if (!validUsername(value)) {
          callback(new Error('请输入正确邮箱格式'))
        } else {
          callback()
        }
      };
      const validatePassword = (rule, value, callback) => {
        if (value.length < 4) {
          callback(new Error('密码不可少于4位'))
        } else {
          callback()
        }
      };
      const validateMobile = (rule, value, callback) => {
        if (!validMobile(value)) {
          callback(new Error('请输入正确手机号码'))
        } else {
          callback()
        }
      };
      return {
        loginForm: {
          username:"",
          password: '', //密码
          mobile: "",// 手机号
          authCode: "",// 验证码
          loginType:'user-username'//登录方式
        },
        loginRules: {
          password: [{required: true, trigger: 'blur', validator: validatePassword}],
          mobile:[{required: true, trigger: 'blur', validator:validateMobile }],
          authCode:[{ required: true, message: '请输入验证码', trigger: 'blur' }],
        },
        cur:true,//true代表密码, false代表短信
        sending: false,
        sendDisabled: false,
        loading: false,
        timer: 0,
        passwordType: 'password',
        showDialog: false,
        redirect: undefined
      };
    },
    computed: {
      sendButtonText() {
        if (this.timer === 0) {
          return "发送验证码";
        } else {
          return `${this.timer}秒后重发`;
        }
      }
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect;
        },
        immediate: true
      },
      timer(num) {
        if (num > 0) {
          setTimeout(() => {
            this.timer = --num;
          }, 1000);
        }
      }
    },
    created() {
      
      const lastSendTime = Cookie.get("last-send-time");
      if (lastSendTime) {
        const timer = 0 ;
        this.timer = timer > 0 ? timer : 0;
      }
      this.wechatLogin();
    },

    methods: {
      wechatLogin(){
        if(window.location.href.indexOf('code')>=0){
          //如果url中包含code,则保存到store中
          let code = window.location.href.split("?")[1];
          this.loginForm.code = code.substring(5,code.indexOf('&'));
          this.loginForm.loginType="user-wechat";
          this.$store.dispatch('user/login',this.loginForm).then(() => {
            this.$router.push({path: this.redirect || '/', query: this.otherQuery});
            this.loading = false
          }).catch(() => {
              this.loginForm.loginType="user-username";
              this.loading = false
            })
        }
      },
      doShow(){
        this.showDialog=true;
        findentErpriseVo().then(response  => {
          const appId = response.data.appId;
          var obj = new WxLogin({
            self_redirect:false,
            id:"loginCode",
            appid: appId,
            scope: "snsapi_login",
            redirect_uri: "http://www.eehp.cn"
          });
        })
      },
      onSendSms() {
        this.loginWay = false;
        this.$refs.loginForm.validateField("mobile", err => {
          if (!err) {
            this.sending = true;
            const {mobile} = this.loginForm;
            Cookie.set("last-send-time", new Date());
            smsSend({'mobile':mobile}).then(() => {
                this.$message.success("短信发送成功，请注意查收");
                Cookie.set("last-send-time", new Date());
                this.timer = 300;
              }).catch(e => {
                this.$message.error("网络异常");
                console.log(e);
              }).finally(() => (this.sending = false)
              );
          }
        });
      },
      showPwd() {
        this.loginWay = true;
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            this.$store.dispatch('user/login',this.loginForm).then(() => {
              this.$router.push({path: this.redirect || '/', query: this.otherQuery});
              this.loading = false
            })
              .catch(() => {
                this.loading = false
              })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
    }
  };
</script>

<style lang="scss">
  .loginCode{
    background-color: #ffffff;
    width: 300px;
    height: 410px;
    margin: 0 auto;
  }
  .login_header a{
    color: #fff;
    margin: 50px;
  }
  .login_header{
    margin-bottom: 30px;
    text-align: center;
  }
  .login_header span{
    margin-right: 20px;
    cursor: pointer;
  }
  .Cbody_item{
    border: 0px solid #999;
    overflow: hidden;
  }
  .login_header a.active{
    color:#fff;
    padding-bottom: 10px;
    border-bottom: 3px solid #fff;
  }


  $bg: #283443;
  $light_gray: #fff;
  $cursor: #fff;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
      color: $cursor;
    }
  }

  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;

      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;
  .social-signup-container {
    margin-left: 410px;
    .sign-btn {
      display: inline-block;
      cursor: pointer;
    }
    .icon {
      color: #fff;
      font-size: 24px;
      margin-top: 8px;
    }
    .wx-svg-container,
    .qq-svg-container {
      display: inline-block;
      width: 40px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      padding-top: 1px;
      border-radius: 4px;
      margin-bottom: 20px;
      margin-right: 5px;
    }
    .wx-svg-container {
      background-color: #24da70;
    }
    .qq-svg-container {
      background-color: #6BA2D6;
      margin-left: 50px;
    }
  }
  .login-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;
    overflow: hidden;

    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 160px 35px 0;
      margin: 0 auto;
      overflow: hidden;
    }

    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;
      margin-left: 340px;
      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }

      .set-language {
        color: #fff;
        position: absolute;
        top: 3px;
        font-size: 18px;
        right: 0px;
        cursor: pointer;
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }

    .thirdparty-button {
      position: absolute;
      right: 0;
      bottom: 6px;
      margin-bottom: 10px;
    }

    @media only screen and (max-width: 470px) {
      .thirdparty-button {
        display: none;
      }
    }
  }
</style>
