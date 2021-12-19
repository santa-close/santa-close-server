import { Resolver, Query, Args, Int, ID } from '@nestjs/graphql';
import { AppMountainService } from './app-mountain.service';
import { MountainItem } from './object/mountain-item.object';
import { MountainCountInput } from './dto/mountain-count.input';

@Resolver(() => MountainItem)
export class AppMountainResolver {
  constructor(private readonly appMountainService: AppMountainService) {}

  @Query(() => [MountainItem], { name: 'mountains' })
  findAll() {
    return this.appMountainService.findAll();
  }

  @Query(() => MountainItem, { name: 'mountain' })
  findOne(@Args('id', { type: () => ID }) id: number) {
    return this.appMountainService.findOne(id);
  }

  @Query(() => Int, { name: 'mountainCount' })
  count(@Args('input') input: MountainCountInput) {
    return 0;
  }
}
