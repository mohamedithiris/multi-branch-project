def call() {
    def changedServices
    def triggeredServices = [:]
    changedServices = sh(script: "git diff --name-only HEAD^ HEAD | sort | uniq", returnStdout: true).trim().split("\n")
    echo "changedServices= $changedServices"
    for (service in changedServices) {
        switch(service) {
            case ~/.*product-composite-service.*/:
                if(!triggeredServices.containsKey('product-composite-service')) {
                    echo "Triggering product-composite-service job"
                    build job: 'recommendation-system-product-composite-service'
                    triggeredServices['product-composite-service'] = true
                }
                break
            case ~/.*product-service.*/:
                if(!triggeredServices.containsKey('product-service')) {
                    echo "Triggering product-service job"
                    build job: 'recommendation-system-product-service'
                    triggeredServices['product-service'] = true
                }
                break
            case ~/.*recommendation-service.*/:
                if(!triggeredServices.containsKey('recommendation-service')) {
                    echo "Triggering recommendation-service job"
                    build job: 'recommendation-system-recommendation-service'
                    triggeredServices['recommendation-service'] = true
                }
                break
            case ~/.*review-service.*/:
                if(!triggeredServices.containsKey('review-service')) {
                    echo "Triggering review-service job"
                    build job: 'recommendation-system-review-service'
                    triggeredServices['review-service'] = true
                }
                break
        }
    }
}
