export class ProcurementDetails {
    category: string;
    description: string;
    maxPrice: number;
    offersDeadline: Date;
    maxOffers: number;
    procurementDeadline: Date;

    constructor(proDetCfg: ProcurementDetailsInterface = {}) {
        this.category= proDetCfg.category;
        this.description= proDetCfg.description;
        this.maxPrice= proDetCfg.maxPrice;
        this.offersDeadline= proDetCfg.offersDeadline;
        this.maxOffers= proDetCfg.maxOffers;
        this.procurementDeadline= proDetCfg.procurementDeadline;
    }
}

interface ProcurementDetailsInterface {
    category?: string;
    description?: string;
    maxPrice?: number;
    offersDeadline?: Date;
    maxOffers?: number;
    procurementDeadline?: Date;
}