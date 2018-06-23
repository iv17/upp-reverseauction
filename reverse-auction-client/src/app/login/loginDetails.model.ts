export class LoginDetails {
    username: string;
    password: string;

    constructor(logDetCfg: LoginDetailsInterface = {}) {
        this.username = logDetCfg.username;
        this.password = logDetCfg.password;
    }
}

interface LoginDetailsInterface {
    username?: string;
    password?: string;
}