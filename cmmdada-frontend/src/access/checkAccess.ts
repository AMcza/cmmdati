import ACCESS_ENUM from "@/access/accessEnum";

const checkAccess = (
  loginUser: API.LoginUserVO, //用户信息
  needAccess = ACCESS_ENUM.NOT_LOGIN //需要权限,默认不需要权限
) => {
  //获取用户权限
  //TODO 语法学习
  const loginUserAccess = loginUser?.userRole ?? ACCESS_ENUM.NOT_LOGIN;
  //判断用户权限
  //如果不需要权限
  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true;
  }
  //如果需要用户权限
  if (needAccess === ACCESS_ENUM.USER) {
    if (loginUserAccess === ACCESS_ENUM.NOT_LOGIN) {
      return false;
    }
    return true;
  }
  //如果需要管理员权限
  if (needAccess === ACCESS_ENUM.ADMIN) {
    if (loginUserAccess !== ACCESS_ENUM.ADMIN) {
      return false;
    }
    return true;
  }
};
export default checkAccess;
