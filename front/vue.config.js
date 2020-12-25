module.exports = {
    baseUrl: './',
    assetsDir: 'static',
    outputDir: 'dist',
    // publicPath: './',
    productionSourceMap: false,
    devServer: {
        proxy: {
            // detail: https://cli.vuejs.org/config/#devserver-proxy
            '/api': {
                target: `http://127.0.0.1:8085/api`,
                changeOrigin: true,
                pathRewrite: {
                    '^/api' : ''
                }
            }
        }
    }
}
