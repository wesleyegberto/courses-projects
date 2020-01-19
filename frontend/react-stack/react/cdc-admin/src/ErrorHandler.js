import PubSub from 'pubsub-js';

export default class ErrorHandler {
  handle(responseError) {
    console.log(responseError);
    if (responseError.status === 400) {
      for (let i = 0; i < responseError.errors.length; i++) {
        var error = responseError.errors[i];
        PubSub.publish('input-error', error);
      }
    }
  }
}