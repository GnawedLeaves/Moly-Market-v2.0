export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    category: string;
    stock: number;
    images: string;
    hasDiscount: boolean;
    discountPercentage: number;
  }