import { Injectable } from '@angular/core';

@Injectable()
export class MessageService {
  messages: string[] = [];

  add(message: string): boolean {
    this.messages.push(message);
    return true;
  }

  clear() {
    this.messages = [];
  }
}
