import { Test, TestingModule } from '@nestjs/testing';
import { AppMountainService } from './app-mountain.service';

describe('AppMountainService', () => {
  let service: AppMountainService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [AppMountainService],
    }).compile();

    service = module.get<AppMountainService>(AppMountainService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
