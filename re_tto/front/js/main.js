import { StringUtils } from "./StringUtils.js";

(() => {
    let aa = 22;
    aa = StringUtils.defaultIfBlank(aa, 33);
    console.log(aa);
})()

