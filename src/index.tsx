import { NativeModules } from 'react-native';

type WorldlinePaymentGatewayType = {
  multiply(a: number, b: number): Promise<number>;
  startPayment(a: {}): Promise<any>;
};

const { WorldlinePaymentGateway } = NativeModules;

export default WorldlinePaymentGateway as WorldlinePaymentGatewayType;
