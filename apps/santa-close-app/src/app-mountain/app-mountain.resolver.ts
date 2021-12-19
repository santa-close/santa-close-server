import { Resolver, Query, Mutation, Args, Int } from '@nestjs/graphql';
import { AppMountainService } from './app-mountain.service';
import { AppMountain } from './entities/app-mountain.entity';
import { CreateAppMountainInput } from './dto/create-app-mountain.input';
import { UpdateAppMountainInput } from './dto/update-app-mountain.input';

@Resolver(() => AppMountain)
export class AppMountainResolver {
  constructor(private readonly appMountainService: AppMountainService) {}

  @Mutation(() => AppMountain)
  createAppMountain(
    @Args('createAppMountainInput')
    createAppMountainInput: CreateAppMountainInput,
  ) {
    return this.appMountainService.create(createAppMountainInput);
  }

  @Query(() => [AppMountain], { name: 'appMountain' })
  findAll() {
    return this.appMountainService.findAll();
  }

  @Query(() => AppMountain, { name: 'appMountain' })
  findOne(@Args('id', { type: () => Int }) id: number) {
    return this.appMountainService.findOne(id);
  }

  @Mutation(() => AppMountain)
  updateAppMountain(
    @Args('updateAppMountainInput')
    updateAppMountainInput: UpdateAppMountainInput,
  ) {
    return this.appMountainService.update(
      updateAppMountainInput.id,
      updateAppMountainInput,
    );
  }

  @Mutation(() => AppMountain)
  removeAppMountain(@Args('id', { type: () => Int }) id: number) {
    return this.appMountainService.remove(id);
  }
}
