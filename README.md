# react-native-worldline-payment-gateway

Worldline payment gateway for Android and iOS

## Installation

```sh
npm install react-native-worldline-payment-gateway
```

## Usage

```js
import WorldlinePaymentGateway from "react-native-worldline-payment-gateway";

// ...

WorldlinePaymentGateway.startPayment({
        order_id: rand.toString(),
        amount: '1300',
        currency: 'INR',
        description: 'Test Transaction from react native',
        transaction_type: 'S',
      }).then(setResponse);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
