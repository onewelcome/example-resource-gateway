package com.onegini.examples.resourcegateway.util;

import com.onegini.examples.resourcegateway.model.DecoratedUserId;

public class DecoratedUserIdBuilder {

  private static final String DECORATION = "✨";

  final DecoratedUserId decoratedUserId;

  public DecoratedUserIdBuilder() {
    this.decoratedUserId = new DecoratedUserId();
  }

  public DecoratedUserIdBuilder withUserId(final String userId) {
    final String userIdWithDecoration = DECORATION + " " + userId + " " + DECORATION;
    decoratedUserId.setDecoratedUserId(userIdWithDecoration);
    return this;
  }

  public DecoratedUserId build() {
    return decoratedUserId;
  }
}
