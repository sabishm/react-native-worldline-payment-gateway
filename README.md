# react-native-worldline-payment-gateway

Worldline payment gateway for Android and iOS

## Installation

```sh
npm install react-native-worldline-payment-gateway
```
```sh
yarn add react-native-worldline-payment-gateway
```

## Usage

## Android

**Add following details in AndroidManifest.xml**
**Note : Replace with your keys**
```xml
 <meta-data
      android:name="com.worldline.paymentgateway.KEY"
      android:value="6a2bxxxxxxxxxxxxxc236" />

      <meta-data
      android:name="com.worldline.paymentgateway.MID"
      android:value="WL000xxxxxxxx06" />

```

## iOS

**Add following details in info.plist**
**Note : Replace with your keys**

```xml
<key>encryptionKey</key>
<string>6a2bxxxxxxxxxxxxxc236</string>
<key>merchantID</key>
<string>WL000xxxxxxxx06</string>
```

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
