package com.onegini.examples.resourcegateway.util;

import com.onegini.examples.resourcegateway.model.DecoratedUser;

public class DecoratedUserIdBuilder {

  private static final String DECORATION = "✨";

  final DecoratedUser decoratedUser;

  public DecoratedUserIdBuilder() {
    this.decoratedUser = new DecoratedUser();
  }

  public DecoratedUserIdBuilder withUserId(final String userId) {
    final String userIdWithDecoration = DECORATION + " " + userId + " " + DECORATION;
    decoratedUser.setDecoratedUserId(userIdWithDecoration);
    return this;
  }

  public DecoratedUser build() {
    return decoratedUser;
  }
}
