export interface Notice {
    heading: string;
    description: string;
    startDate: Date;
    expiryDate: Date;
    active: boolean;
    image?: string;
}