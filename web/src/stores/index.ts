import { createPinia, defineStore } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import Base64 from "crypto-js/enc-base64";
import Utf8 from "crypto-js/enc-utf8";
import { SysMenu, TokenInfo } from "#/entity.ts";

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const useStore = defineStore("main", {
  persist: true,
  state: (): { tokenInfo: TokenInfo, token: string, menuList: SysMenu[] } => ({
    tokenInfo:  undefined,
    token: "",
    menuList: undefined,
  }),
  actions: {
    /**
     *
     * @param tokenStr{string} token字符串
     */
    setTokenStr(tokenStr: string): void {
      const tokenData = tokenStr.split(".");
      this.token = tokenData[2];
      const tokenInfoData = JSON.parse(Base64.parse(tokenData[1]).toString(Utf8));
      tokenInfoData.userData = JSON.parse(tokenInfoData.userData);
      this.tokenInfo = tokenInfoData;
    },
    setMenuList(menuList: SysMenu[]): void {
      this.menuList = menuList;
    },
    setTokenInfo(tokenInfo: TokenInfo): void {
      this.tokenInfo = tokenInfo;
    },
    getToken(): string {
      return this.token;
    },
    getTokenInfo(): TokenInfo {
      return this.tokenInfo;
    },
    getMenuList(): SysMenu[] {
      return this.menuList;
    },
    init(){
      this.tokenInfo = undefined;
      this.token = undefined;
    }
  },
});

export default useStore;