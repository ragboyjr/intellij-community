/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * User: anna
 * Date: 27-Dec-2007
 */
package com.intellij.openapi.roots;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.extensions.ExtensionPointName;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * Implement {@link com.intellij.openapi.components.PersistentStateComponent} to be serializable.
 */
public abstract class ModuleExtension implements Disposable, Comparable<ModuleExtension> {
  public static final ExtensionPointName<ModuleExtension> EP_NAME = ExtensionPointName.create("com.intellij.moduleExtension");

  /**
   * <b>Note:</b> don't call this method directly from client code. Use approach below instead:
   * <pre>
   *   ModifiableRootModel modifiableModel = ModuleRootManager.getInstance(module).getModifiableModel();
   *   CompilerModuleExtension extension = modifiableModel.getModuleExtension(CompilerModuleExtension.class);
   *   try {
   *     // ...
   *   }
   *   finally {
   *     modifiableModel.commit();
   *   }
   * </pre>
   * The point is that call to commit() on CompilerModuleExtension obtained like
   * <code>'CompilerModuleExtension.getInstance(module).getModifiableModel(true)'</code> doesn't dispose the model.
   * <p/>
   * Call to <code>ModifiableRootModel.commit()</code> not only commits linked extensions but disposes them as well.
   * 
   * @param writable  flag which identifies if resulting model is writable
   * @return          extension model
   */
  public abstract ModuleExtension getModifiableModel(final boolean writable);

  public abstract void commit();

  public abstract boolean isChanged();

  @Override
  public int compareTo(@NotNull final ModuleExtension o) {
    return getClass().getName().compareTo(o.getClass().getName());
  }

  /**
   * @deprecated Please implement PersistentStateComponent instead.
   */
  @Deprecated
  public void readExternal(@NotNull Element element) {
    throw new Error("Please implement PersistentStateComponent");
  }

  /**
   * @deprecated Please implement PersistentStateComponent instead.
   */
  @Deprecated
  public void writeExternal(@NotNull Element element) {
    throw new Error("Please implement PersistentStateComponent");
  }
}