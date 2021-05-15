import { MessageService } from './message.service';
import { CalculationService } from './calculation.service';

describe('CalculationService', () => {
  let mockMessageService: any;

  let service: CalculationService;

  beforeEach(() => {
    mockMessageService = jasmine.createSpyObj(['add']);
    service = new CalculationService(mockMessageService);
  });

  describe('calculate', () => {
    it('should return 42 and log it', () => {
      mockMessageService.add.and.returnValue(true);

      expect(service.calculate()).toBe(42);

      expect(mockMessageService.add).toHaveBeenCalledTimes(1);
      expect(mockMessageService.add).toHaveBeenCalledWith('Calculation result: 42');
    });
  });
});
