import { Test, TestingModule } from '@nestjs/testing';
import { AppMountainResolver } from './app-mountain.resolver';
import { AppMountainService } from './app-mountain.service';

describe('AppMountainResolver', () => {
  let resolver: AppMountainResolver;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [AppMountainResolver, AppMountainService],
    }).compile();

    resolver = module.get<AppMountainResolver>(AppMountainResolver);
  });

  it('should be defined', () => {
    expect(resolver).toBeDefined();
  });
});
