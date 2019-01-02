package viewModels;

import services.CommonService;

public class LoginViewModel{

    private CommonService commonService;

    public LoginViewModel(CommonService commonService) {
        this.commonService = commonService;
    }

    public void Login(String nickName) {
        this.commonService.saveUser(nickName);
        this.commonService.loggedInUser.setValue(nickName);
    }
}
