import { Resolver, Query, Args, Int, ID } from '@nestjs/graphql';
import { AppMountainService } from './app-mountain.service';
import { Mountain } from './object/mountain.object';

@Resolver(() => Mountain)
export class AppMountainResolver {
  constructor(private readonly appMountainService: AppMountainService) {}

  @Query(() => [Mountain], { name: 'mountains' })
  findAll() {
    return this.appMountainService.findAll();
  }

  @Query(() => Mountain, { name: 'mountain' })
  findOne(@Args('id', { type: () => ID }) id: number) {
    return this.appMountainService.findOne(id);
  }

  @Query(() => Int, { name: 'mountainCount' })
  count() {
    return 0;
  }
}
