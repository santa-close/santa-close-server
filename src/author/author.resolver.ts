import { Resolver, Query, Mutation, Args } from '@nestjs/graphql';
import { AuthorService } from './author.service';
import { CreateAuthorInput } from './dto/create-author.input';
import { UpdateAuthorInput } from './dto/update-author.input';

@Resolver('Author')
export class AuthorResolver {
  constructor(private readonly authorService: AuthorService) {}

  @Mutation('createAuthor')
  create(@Args('createAuthorInput') createAuthorInput: CreateAuthorInput) {
    return this.authorService.create(createAuthorInput);
  }

  @Query('author')
  findAll() {
    return this.authorService.findAll();
  }

  @Query('author')
  findOne(@Args('id') id: number) {
    return this.authorService.findOne(id);
  }

  @Mutation('updateAuthor')
  update(@Args('updateAuthorInput') updateAuthorInput: UpdateAuthorInput) {
    return this.authorService.update(updateAuthorInput.id, updateAuthorInput);
  }

  @Mutation('removeAuthor')
  remove(@Args('id') id: number) {
    return this.authorService.remove(id);
  }
}
