import { Test, TestingModule } from '@nestjs/testing';
import { SantaCloseAppController } from './santa-close-app.controller';
import { SantaCloseAppService } from './santa-close-app.service';

describe('SantaCloseAppController', () => {
  let santaCloseAppController: SantaCloseAppController;

  beforeEach(async () => {
    const app: TestingModule = await Test.createTestingModule({
      controllers: [SantaCloseAppController],
      providers: [SantaCloseAppService],
    }).compile();

    santaCloseAppController = app.get<SantaCloseAppController>(
      SantaCloseAppController,
    );
  });

  describe('root', () => {
    it('should return "Hello World!"', () => {
      expect(santaCloseAppController.getHello()).toBe('Hello World!');
    });
  });
});
