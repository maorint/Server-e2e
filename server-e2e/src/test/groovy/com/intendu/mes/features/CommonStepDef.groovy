package com.intendu.mes.features

import com.intendu.mes.features.support.MyWorld
import com.intendu.mes.features.support.services.MongoService

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

World {
    new MyWorld()
}
Before("@UsingBrowser"){
    intenduWeb.seleniumHelper.start()

}

After {
    blocks.each({
        MongoService.client().removeByInternalId(it.internalId)
    })
}

After("@UsingBrowser"){
    intenduWeb.seleniumHelper.terminate()
}