import SwiftUI
import KAppDeeplink

struct ModuleExampleView: View {

    @State private var showContent = false
    var body: some View {
        VStack {
            Text("KappDeeplink Module Example")
                .font(.title3)
            Text("github.com/lucasferreiramachado/kapp-deeplink")
                .font(.footnote)
                .padding(4)

            Button("Click me!") {
                withAnimation {
                    showContent = !showContent
                }
            }
            .padding()
            if showContent {
                VStack(spacing: 16) {
                    Image(systemName: "swift")
                        .font(.system(size: 200))
                        .foregroundColor(.accentColor)
                }
                .transition(.move(edge: .top).combined(with: .opacity))
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
    }
}

struct ModuleExampleView_Previews: PreviewProvider {
    static var previews: some View {
        ModuleExampleView()
    }
}
