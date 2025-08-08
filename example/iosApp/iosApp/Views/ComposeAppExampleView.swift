import SwiftUI
import KAppDeeplinkExample

struct ComposeAppExampleView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        ComposeAppViewControllerKt.ComposeAppViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
