export class CompanyDetails {
    agent: string;
    categories: any[];
    distance: number;

    constructor(comDetCfg: CompanyDetailsInterface = {}) {
        this.agent = comDetCfg.agent;
        this.categories = comDetCfg.categories;
        this.distance = comDetCfg.distance;
    }
}

interface CompanyDetailsInterface {
    agent?: string;
    categories?: any[];
    distance?: number;
}