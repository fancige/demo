<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib prefix="locale" uri="/WEB-INF/locale.tld"%>
<locale:set page="header0" />
<div id="header0">
  <div id="h0_header">
    <b id="h0_logo">fancige</b> <img src="/mail/images/setting.png" id="h0_imgSetting">
  </div>
  <div id="h0_divSetting" style="display: none;">
    <form action="/mail/preference" method="post">
      ${h0_1} <select name="locale"><option value="en_US">${h0_2}</option>
        <option value="zh_CN">${h0_3}</option></select>
      <button type="submit">${h0_4}</button>
    </form>
  </div>
</div>