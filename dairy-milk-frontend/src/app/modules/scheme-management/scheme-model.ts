export interface Scheme {
  id?: number;
  schemeName: string;
  schemeType: string;      // INCENTIVE | LOAN | ADVANCE | SUBSIDY | FEED
  description?: string;
  valueType: string;       // FIXED | PERCENTAGE
  value: number;           // ₹ amount ya % 
  startDate: string;       // yyyy-MM-dd
  endDate: string;
  active: boolean;
}