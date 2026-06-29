export type MilkType = 'COW' | 'BUFFALO';

export interface Rate {
  id?: number;
  milkType: MilkType;
  minFat: number;
  maxFat: number;
  ratePerLitre: number;
  active?: boolean;
}