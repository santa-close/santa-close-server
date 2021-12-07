import { Test, TestingModule } from '@nestjs/testing';
import { AuthorResolver } from './author.resolver';
import { AuthorService } from './author.service';

describe('AuthorResolver', () => {
  let resolver: AuthorResolver;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [AuthorResolver, AuthorService],
    }).compile();

    resolver = module.get<AuthorResolver>(AuthorResolver);
  });

  it('should be defined', () => {
    expect(resolver).toBeDefined();
  });
});
