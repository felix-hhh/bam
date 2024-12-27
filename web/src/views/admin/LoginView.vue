<script setup lang="ts">
import { onMounted, ref } from "vue";
import { FormItemRule } from "element-plus";
import { LoginData } from "#/entity.ts";
import { MD5 } from "crypto-js";
import useAxios from "@/api";
import router from "@/router";
import useStore from "@/stores";

const loginFormData = ref<LoginData>({ identity: "", password: "", username: "", verifyCode: "" });
const loginFormRef = ref();
const imageUrl = ref<String>();
const rules = {
  username: [
    {
      required: true,
      message: "请输入用户名",
      trigger: "blur",
    },
    {
      min: 5,
      max: 20,
      message: "长度必须在 5 至 20 个字符",
      trigger: "blur",
    },
  ],
  password: [
    {
      required: true,
      message: "请输入密码",
      trigger: "blur",
    },
  ],
  verifyCode: [
    {
      required: true,
      message: "请输入验证码",
      trigger: "blur",
    },
  ],
};
const { sendPost } = useAxios();
const store = useStore();

const getVerifyCodeImageUrl = () => {
  const now = Date.now();
  let url = import.meta.env.VITE_API_URL + `/system/manage/admin/info/image/${now.toString()}`;
  loginFormData.value.identity = now.toString();
  imageUrl.value = url;
};

/**
 * 登录
 */
const loginHandler = async () => {
  await loginFormRef.value.validate((valid, fields) => {
    if (valid) {
      const submitData = JSON.parse(JSON.stringify(loginFormData.value));
      submitData.password = MD5(submitData.password).toString().toLowerCase();
      sendPost("/system/manage/admin/info/login", submitData)
        .then((rep: any) => {
          store.setTokenStr(rep);
          router.push("/index");
        })
        .catch(() => {
          getVerifyCodeImageUrl();
          loginFormData.value.password = "";
          loginFormData.value.verifyCode = "";
        });
    } else {
      console.log("error submit!", fields);
    }
  });
};

onMounted(() => {
  getVerifyCodeImageUrl();
});
</script>

<template>
  <div class="login-view">
    <div class="login-view-panel">
      <div class="login-view-panel-header">
        <h1>系统登录</h1>
      </div>
      <div class="login-view-panel-body">
        <el-form
          ref="loginFormRef"
          :rules="rules"
          :model="loginFormData"
        >
          <el-form-item class="login-view-panel-body-item" size="large" prop="username">
            <el-input
              class="login-view-panel-body-input"
              prefix-icon="User"
              v-model="loginFormData.username"
              placeholder="账号"
            />
          </el-form-item>
          <el-form-item class="login-view-panel-body-item" size="large" prop="password">
            <el-input
              class="login-view-panel-body-input"
              prefix-icon="Key"
              type="password"
              v-model="loginFormData.password"
              placeholder="密码"
            />
          </el-form-item>
          <el-row class="login-view-panel-body-item">
            <el-col :span="12">
              <el-form-item size="large" prop="verifyCode">
                <el-input
                  class="login-view-panel-body-input"
                  prefix-icon="Loading"
                  v-model="loginFormData.verifyCode"
                  placeholder="验证码"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12" class="auth-img">
              <el-image :src="imageUrl" fit="fill" @click="getVerifyCodeImageUrl">
                <template #placeholder>
                  <div class="image-slot">Loading<span class="dot">...</span></div>
                </template>
                <template #error>
                  <div class="image-slot">
                    <el-icon>
                      <Picture />
                    </el-icon>
                  </div>
                </template>
              </el-image>
            </el-col>
          </el-row>
          <el-form-item size="large" class="login-view-panel-body-item">
            <el-button type="primary" auto-insert-space @click="loginHandler" class="login-view-panel-body-submit">
              登录
            </el-button>
          </el-form-item>
        </el-form>

      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-view {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;

  &-panel {
    width: 600px;
    background-color: var(--el-color-white);
    padding: 32px 24px;
    box-shadow: var(--el-box-shadow-light);
    border-radius: var(--el-border-radius-base);

    &-header {
      color: var(--el-color-primary);
      text-align: center;
      font-weight: bold;
    }

    &-body {
      margin: 30px 100px;

      &-item {
        margin: 25px 35px 0;
      }

      &-input {
        height: 45px;
      }

      &-submit {
        width: 100%;
        height: 50px;
        font-size: var(--el-font-size-large);
      }

      .auth-img {
        height: 45px;
        padding-left: 10px;

        .el-image {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>