export class RegistrationDetails {
    name: string;
    surname: string;
    email: string;
    username: string;
    password: string;
    address: string;
    place: string;
    zipCode: string;
    isCompany: boolean;
    lat: string;
    lng: string;

    constructor(regDetCfg: RegistrationDetailsInterface = {}) {
        this.name = regDetCfg.name;
        this.surname = regDetCfg.surname;
        this.email = regDetCfg.email;
        this.username = regDetCfg.username;
        this.password = regDetCfg.password;
        this.address = regDetCfg.address;
        this.place = regDetCfg.place;
        this.zipCode = regDetCfg.zipCode;
        this.isCompany = regDetCfg.isCompany || false;
        this.lat = regDetCfg.lat;
        this.lng = regDetCfg.lng;
    }
}

interface RegistrationDetailsInterface {
    name?: string;
    surname?: string;
    email?: string;
    username?: string;
    password?: string;
    address?: string;
    place?: string;
    zipCode?: string;
    isCompany?: boolean;
    lat?: string;
    lng?: string;
}