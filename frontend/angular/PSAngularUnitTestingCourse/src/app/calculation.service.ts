import { Injectable } from '@angular/core';
import { MessageService } from './message.service';

@Injectable()
export class CalculationService {
  constructor(private readonly messageService: MessageService) {}

  calculate(): number {
    const result = this.messageService.add('Calculation result: 42');
    if (!result) {
      throw new Error('Result was not added');
    }
    return 42;
  }
}
