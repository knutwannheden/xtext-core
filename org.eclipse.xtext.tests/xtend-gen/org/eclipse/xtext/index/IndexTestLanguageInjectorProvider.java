/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.index;

import com.google.inject.Injector;
import org.eclipse.xtext.index.IndexTestLanguageStandaloneSetup;
import org.eclipse.xtext.testing.GlobalRegistries;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.IRegistryConfigurator;

/**
 * @author Sven Efftinge - Initial contribution and API
 */
@SuppressWarnings("all")
public class IndexTestLanguageInjectorProvider implements IInjectorProvider, IRegistryConfigurator {
  protected GlobalRegistries.GlobalStateMemento stateBeforeInjectorCreation;
  
  protected GlobalRegistries.GlobalStateMemento stateAfterInjectorCreation;
  
  protected Injector injector;
  
  public IndexTestLanguageInjectorProvider() {
    GlobalRegistries.initializeDefaults();
  }
  
  @Override
  public Injector getInjector() {
    if ((this.injector == null)) {
      this.stateBeforeInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
      this.injector = this.internalCreateInjector();
      this.stateAfterInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
    }
    return this.injector;
  }
  
  protected Injector internalCreateInjector() {
    return new IndexTestLanguageStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
  
  @Override
  public void restoreRegistry() {
    this.stateBeforeInjectorCreation.restoreGlobalState();
  }
  
  @Override
  public void setupRegistry() {
    this.getInjector();
    this.stateAfterInjectorCreation.restoreGlobalState();
  }
}
